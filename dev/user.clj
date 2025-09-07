(ns user
  (:require
   [hkimjp.carmine :as c]
   [java-time.api :as jt]
   [taoensso.telemere :as t]))

(t/set-min-level! :debug)

(comment
  (c/redis-server)
  (c/ping)
  (c/set "x" 1)
  (c/get "x")
  (c/set "now" (java.util.Date.))
  (c/get "now")
  (c/setex "y" 10 (jt/local-time))
  (str (c/get "y"))
  :rcf)
