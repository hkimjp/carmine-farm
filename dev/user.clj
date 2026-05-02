(ns user
  (:require
   [hkimjp.carmine :as c]
   [taoensso.telemere :as tel]))

(tel/set-min-level! :debug)

(comment
  (c/create-conn)
  (c/ping)
  (c/close-conn)
  :rcf)
