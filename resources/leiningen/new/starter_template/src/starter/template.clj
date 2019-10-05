(ns {{name}}.template
  (:require [{{name}}.config :as config]
            [{{name}}.middleware :as middleware]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [selmer.parser :as parser]))


(parser/add-tag! :csrf-field (fn [_ _] (anti-forgery-field)))

(defn render-file [req template map]
  (let [authenticated (middleware/check-authenticated req)
        admin (= (config/admin-email) (middleware/get-identity req))]
    (parser/render-file template (assoc map :authenticated authenticated :admin admin :email (if authenticated (middleware/get-identity req) (:email map))))))
