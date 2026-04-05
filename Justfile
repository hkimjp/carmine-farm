set dotenv-load

help:
  just --list

nrepl:
  clj -M:dev:nrepl

dev: nrepl

clean:
  rm -rf target
