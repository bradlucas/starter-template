(ns {{name}}.middleware
  (:require [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [clojure.pprint :as pp]
            [ring-ttl-session.core :refer [ttl-memory-store]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.util.response :as response]
            [selmer.parser :as parser]))


(defn error-page
  "error-details should be a map containing the following keys:
   :status - error status
   :title - error title (optional)
   :message - detailed error message (optional)

   returns a response map with the error page as the body
   and the status specified by the status key"
  [error-details]
  {:status  (:status error-details)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    (parser/render-file "templates/static/error.html" error-details)})

(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        ;;(log/error t (.getMessage t))
        (pp/pprint (.getMessage t))
        (error-page {:status 500
                     :title "Something very bad has happened!"
                     :message "We've dispatched a team of highly trained gnomes to take care of the problem."})))))

(defn wrap-csrf [handler]
  (wrap-anti-forgery
    handler
    {:error-response
     (error-page
       {:status 403
        :title "Invalid anti-forgery token"})}))

(defn on-error [request response]
  ;; Next version redirects to /login with a next parameter with the requested page
  (response/redirect (str "/login" "?next=" (:uri request))))


(defn get-identity [request]
  (let [v (:identity (:session request))]
    (if v (name v))))

(defn check-authenticated [request]
  ;; buddy.auth.authenticated? looks for :identity in the request
  ;; @see https://github.com/funcool/buddy-auth/blob/master/src/buddy/auth.clj
  ;; (pp/pprint (str (boolean (:identity request))))

  ;; we are storing :identity in the session
  ;; (pp/pprint (str (boolean (:identity (:session request)))))
  (boolean (:identity (:session request))))

(defn wrap-restricted [handler]
  (restrict handler {:handler check-authenticated
                     :on-error on-error}))

(defn wrap-auth [handler]
  (let [backend (session-backend)]
    (-> handler
        (wrap-authentication backend)
        (wrap-authorization backend))))


(defn wrap-base [handler]
  (-> handler 
      wrap-auth
      (wrap-defaults
        (-> site-defaults
            (assoc-in [:security :anti-forgery] true)
            (assoc-in [:session :cookie-name] "ring-session-{{name}}")
            (assoc-in [:session :store] (ttl-memory-store (* 60 30)))))
      wrap-internal-error))
