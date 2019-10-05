(ns {{name}}.handler
  (:require [compojure.core :refer [routes GET defroutes]]
            [compojure.route :as route]
            [{{name}}.routes.auth :as auth]
            [{{name}}.routes.account :as account]
            [{{name}}.routes.static :as static]
            [{{name}}.middleware :as middleware]))


(def app
  (middleware/wrap-base (routes static/routes
                                auth/routes
                                (middleware/wrap-restricted account/routes)
                                (route/not-found "Not Found"))))


