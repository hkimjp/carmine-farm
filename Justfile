set dotenv-load

help:
  just --list

nrepl:
  clj -M:dev:nrepl

dev: nrepl

subl:
  subl .
  just dev

clean:
  rm -rf target
