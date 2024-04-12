(ns notes-app.renderer)

(defn get-frettings-for-fret [wanted-fret fingerings]
  (let [frettings (map (fn [[string {:keys [fret]}]]
                         (when (= wanted-fret fret) true)) fingerings)]
    frettings))

(def empty-fret "-----")
(def active-fret "--O--")
(def divider "|")
(defn note-fret [note]
  (let [length (count (str note))]
    (if (= 1 length) (str "--" note "--")
        (str "--" note "-"))))
(def pipe "|")

(print (note-fret "A") pipe)
(print active-fret pipe)

(defn- render-frets [fret-states]
  (doseq [state fret-states]
    (if state (print active-fret) (print empty-fret))
    (print divider)))

(defn- render-notes [notes]
  (doseq [note notes]
    (print (note-fret note))
    (print divider)))

(defn render [fingerings]
  (let [fret-values (map :fret (vals fingerings))
        min-fret (apply min fret-values)
        max-fret (apply max fret-values)
        fret-range (range min-fret (inc max-fret))]
    (doseq [fret fret-range]
      (let [frettings (get-frettings-for-fret fret fingerings)]
        (print fret pipe)
        (render-frets frettings)
        (println)))
    (print "n" pipe)
    (render-notes (map (comp name :note) (vals fingerings)))
    (println)))
