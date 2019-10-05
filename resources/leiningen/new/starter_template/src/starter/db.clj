(ns {{name}}.db
  (:require [conman.core :as conman]
            [mount.core :as mount :refer [defstate]]))


;; https://jdbc.postgresql.org/documentation/80/connect.html
(def pool-spec
  {:jdbc-url   "jdbc:postgresql://localhost/{{name}}"})

(defstate ^:dynamic *db*
          :start (conman/connect! pool-spec)
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/queries.sql")
