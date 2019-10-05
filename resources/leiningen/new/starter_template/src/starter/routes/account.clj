(ns {{name}}.routes.account
  (:require [clojure.pprint :as pp]
            [compojure.core :refer [defroutes GET]]
            [{{name}}.db :as db]
            [{{name}}.template :as template]))


(defn profile [req]
  (let [email (name (:identity (:session req)))
        account (db/get-account {:email email})]
    (template/render-file req "templates/account/profile.html" {:account account})))


(defroutes routes
  (GET "/account" [] profile))
