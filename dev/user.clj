(ns user
  (:require
   [hkimjp.carmine :as c]
   [taoensso.telemere :as tel]))

(tel/set-min-level! :debug)

(comment
  (c/create-conn)
  (c/ping)
  (c/keys "*")
  (count (c/keys "*"))
  (c/scan0 "commented:*")

  (c/set "a" 1)
  (c/get "a")
  (c/del "a")
  (c/get "a")

  (c/close-conn)
  :rcf)
