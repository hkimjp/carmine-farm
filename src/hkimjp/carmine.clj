(ns hkimjp.carmine
  (:refer-clojure :exclude [set get keys])
  (:require
   [clojure.string :as str]
   ; [java-time.api :as jt]
   [taoensso.carmine :as car]
   [taoensso.telemere :as t]))

(defonce my-conn-pool (car/connection-pool {}))

(def my-conn-spec {:uri (or (System/getenv "REDIS") "redis://localhost:6379")})

(def my-wcar-opts {:pool my-conn-pool, :spec my-conn-spec})

(defmacro wcar* [& body] `(car/wcar my-wcar-opts ~@body))

(defn ping []
  (wcar* (car/ping)))

(defn set [key value]
  (wcar* (car/set key value)))

(defn setex [key expire value]
  (wcar* (car/setex key expire value)))

(defn get [key]
  (wcar* (car/get key)))

(defn keys [key]
  (wcar* (car/keys key)))

(defn ttl [key]
  (wcar* (car/ttl key)))

(defn lpush [key element]
  (wcar* (car/lpush key element)))

(defn lrange
  ([key] (lrange key 0 -1))
  ([key start stop]
   (wcar* (car/lrange key start stop))))

(defn llen [key]
  (wcar* (car/llen key)))

;-----------------------

(defn ping? []
  (try
    (ping)
    (catch Exception e
      (println (.getMessage e))
      nil)))

; (ping?)
