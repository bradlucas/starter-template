(ns {{name}}.config
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))


(def root-url-value (atom nil))

(defn root-url []
  @root-url-value)

(def admin-email-value (atom nil))

(defn admin-email []
  @admin-email-value)

(def port-value (atom nil))

(defn port []
  @port-value)

(defn load-config-file []
  (let [filename (str (System/getProperty "user.home") "/.{{name}}/config.edn")]
    (if (.exists (io/as-file filename))
      (let [m (edn/read-string (slurp filename))]
        m)
      {:root-url "http://foo.com"
       :admin-email "admin@foo.com"
       :port 4000})))

(defn load-config []
  (let [m (load-config-file)]
    (reset! root-url-value (:root-url m))
    (reset! admin-email-value (:admin-email m))
    (reset! port-value (:port m))))
