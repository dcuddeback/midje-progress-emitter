(ns midje-progress-emitter.namespace)

(def ^:private previous-namespace (atom nil))

(defn set-namespace! [name]
  (reset! previous-namespace name))

(defn forget-namespace! []
  (set-namespace! nil))

(defn new-namespace? [name]
  (not= @previous-namespace name))
