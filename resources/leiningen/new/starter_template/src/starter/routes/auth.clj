(ns {{name}}.routes.auth
  (:require [buddy.core.codecs :as codecs]
            [buddy.core.hash :as hash]
            [clojure.pprint :as pp]
            [compojure.core :refer [defroutes GET POST]]
            [{{name}}.db :as db]
            ;;[{{name}}.notify :as notify]
            [{{name}}.template :as template]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.util.response :as response]))


(defn sha [s]
  (-> (hash/sha256 s)
      (codecs/bytes->hex)
      (->>
       (apply str "\\x"))))      ;; insert sha256(value) in postgres returns value with a leading \\x

(defn login [req]
  (if (= (:request-method req) :get)
    ;; get - show login form
    (template/render-file req "templates/auth/login.html" {:csrf-token *anti-forgery-token* :next-url (get-in req [:query-params "next"] "/account")})
    ;; else post - respond to login request
    (let [params (:params req)
          email (:email params)
          password (:password params)
          next-url (:next-url params)
          session (:session req)]
      (let [account (db/get-account {:email email})]
        (if account
          ;; user exists
          (if (= (:password account) (sha password))
            ;; valid password - log the user in
            (do
              (pp/pprint (format "Account login %s" email))
              ;;(notify/notify-admin-login account)
              (let [updated-session (assoc session :identity (keyword email))]
                (-> (response/redirect next-url)
                    (assoc :session updated-session))))
            ;; else - error in password
            (template/render-file req "templates/auth/login-error-password.html" {:email email}))
          ;; else - no account found with that email
          (template/render-file req "templates/auth/login-error-email.html" {:email email}))))))

(defn logout [req]
  (-> (response/redirect "/")
      (assoc :session {})))


(defroutes routes
  (GET "/login" [] login)
  (POST "/login" [] login)

  (GET "/logout" [] logout))
