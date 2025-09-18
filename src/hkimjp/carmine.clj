(ns hkimjp.carmine
  (:refer-clojure :exclude [set get keys])
  (:require
   [environ.core :refer [env]]
   [taoensso.carmine :as car]
   [taoensso.telemere :as t]))

(defmacro wcar* [& body] `(car/wcar my-wcar-opts ~@body))
(def my-conn-pool nil)
(def my-conn-spec nil)
(def my-wcar-opts nil)

(defn create-conn
  ([] (create-conn (or (env :redis) "redis://localhost:6379")))
  ([uri]
   (t/log! {:level :info :id "create-conn" :data {:uri uri}})
   (try
     (alter-var-root #'my-conn-pool (constantly (car/connection-pool {})))
     (alter-var-root #'my-conn-spec (constantly {:uri uri}))
     (alter-var-root #'my-wcar-opts
                     (constantly {:pool my-conn-pool :spec my-conn-spec}))
     (catch Exception e
       (t/log! {:level :fatal :msg e})
       (System/exit 0)))))

(def redis-server create-conn)

; FIXME: really closed?
(defn close-conn []
  (t/log! {:level :info :id "close-conn"})
  (alter-var-root #'my-conn-pool (constantly nil))
  (alter-var-root #'my-conn-spec (constantly nil))
  (alter-var-root #'my-wcar-opts (constantly nil)))

(defn ping []
  (t/log! :debug "ping")
  (wcar* (car/ping)))

(defn set [key value]
  (t/log! :debug (str "set " key " " value))
  (wcar* (car/set key value)))

(defn setex [key expire value]
  (t/log! :debug (str "setex " key " " expire " " value))
  (wcar* (car/setex key expire value)))

(defn expire [key value]
  (t/log! :debug (str "expire " key " " value))
  (wcar* (car/expire key value)))

(defn incr [counter]
  (t/log! :debug "counter")
  (wcar* (car/incr counter)))

(defn ttl [key]
  (t/log! :debug (str "ttl " key))
  (wcar* (car/ttl key)))

(defn get [key]
  (t/log! :debug (str "get " key))
  (wcar* (car/get key)))

(defn exist? [key]
  (some? (get key)))

(defn keys [key]
  (t/log! :debug (str "keys " key))
  (wcar* (car/keys key)))

(defn lpush [key element]
  (t/log! :debug (str "lpush " key " " element))
  (wcar* (car/lpush key element)))

(defn lrange
  ([key] (lrange key 0 -1))
  ([key start stop]
   (t/log! :debug (str "lrange " key " " start " " stop))
   (wcar* (car/lrange key start stop))))

(defn llen [key]
  (t/log! :debug (str "llen " key))
  (wcar* (car/llen key)))
