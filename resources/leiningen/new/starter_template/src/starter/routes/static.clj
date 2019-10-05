(ns {{name}}.routes.static
  (:require [clojure.pprint :as pp]
            [compojure.core :refer [defroutes GET POST]]
            [{{name}}.template :as template]))


(defn index [req]
  (template/render-file req "templates/static/index.html" {}))

(defn about [req]
  (template/render-file req "templates/static/about.html" {}))


(defroutes routes
  (GET "/" [] index)
  (GET "/about" [] about))
