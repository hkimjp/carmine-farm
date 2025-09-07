(ns user
  (:require
   [hkimjp.carmine :as c]
   [java-time.api :as jt]
   [taoensso.telemere :as t]))

(t/set-min-level! :debug)

(comment
  (require '[hkimjp.carmine :as c])
  (c/redis-server)
  (c/ping)
  (c/set "x" 1)
  (c/get "x")
  (c/setex "now" 10 (java.util.Date.))
  (c/get "now")
  (c/get "now")
  :rcf)
