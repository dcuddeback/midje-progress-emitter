(ns midje-progress-emitter.color
  (:require [midje.emission.colorize :as colorize]
            [colorize.core :as color]))

(def pass colorize/pass)

(def fail colorize/fail)

(def note colorize/note)

(def warn color/yellow)
