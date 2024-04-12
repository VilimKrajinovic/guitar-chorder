(ns notes-app.scales
  (:require [notes-app.notes :as notes]))

(def h 1)
(def W 2)
(def Wh 3)

(def scales {:chromatic (repeat 12 h)
             :major [W W h W W W h]
             :major|pentatonic [W W Wh W Wh]
             :minor|pentatonic [Wh W W Wh W]
             :minor|natural [W h W W h W W]
             :minor|harmonic [W h W W h Wh h]})

(defn note-series [root]
  (drop-while (complement root) (cycle notes/ordered-notes)))

(defn take-nths [nths coll]
  (let [indicies (reductions + 0 nths)]
    (map (partial nth coll) indicies)))

(defn scale [scale root]
  (drop-last (take-nths (scales scale) (note-series root))))

(comment
  (scale :major :G))