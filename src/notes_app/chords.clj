(ns notes-app.chords
  (:require [clojure.set :as set]
            [notes-app.scales :as scales]))

(def chords {:M [:major [1 3 5]]
             :M7 [:major [1 3 5 7]]
             :m [:minor|natural [1 3 5]]
             :m7 [:minor|natural [1 3 5 7]]})

(defn- chord-form->pretty-string [chord-form]
  (case chord-form
    :M7 "maj7"
    :m7 "m7"
    :m "m"
    :M "maj"))

(defn- build-chord-name [chord root]
  (str (name root) (chord-form->pretty-string chord)))

(defn chord [chord root]
  (let [[scale-name indexes] (chords chord)
        scale (scales/scale scale-name root)]
    {:name (build-chord-name chord root)
     :notes (apply set/union (map (comp (partial nth scale) dec) indexes))}))


(comment
  (chord :M :G))
