(ns user
  (:require
   [hkimjp.carmine :as c]
   [taoensso.telemere :as tel]))

(tel/set-min-level! :debug)

(c/create-conn)

(comment

  (dotimes [n 10]
    (c/set (str "x" n) n))

  (c/keys "x*")
  (c/scan "x*")
  (c/scan0 "x*")

  (c/get "x8")

  (c/exists "x11")

  c/my-conn-pool
  c/my-conn-spec
  c/my-wcar-opts

  (c/close-conn)
  :rcf)

(comment
  (c/create-conn)

  (c/close-conn)
  (c/set "x" 200)
  (c/get "x")
  (c/incr "x")
  (c/expire "x" 10)
  (c/ttl "x")
  (c/llist "x")

  (c/close-conn)
  :rcf)
