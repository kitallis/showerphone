(ns showerphone.core
  (:require [overtone.live :refer :all]
            [overtone.inst.sampled-piano :refer :all]))

(defn play-chord [chord]
  (doseq [note chord] (sampled-piano note)))

(defonce one-twenty-bpm (metronome 120))

(defn chord-progression-beat [nome]
  (let [beat (nome)]
    (at (nome (+ 0 beat)) (play-chord (chord :C4 :major)))
    (at (nome (+ 4 beat)) (play-chord (chord :G3 :major)))
    (at (nome (+ 6 beat)) (play-chord (chord :F3 :sus4)))
    (apply-by (nome (+ 8 beat)) chord-progression-beat nome (+ 8 beat) []))
)

(def kick
  (sample (freesound-path 777)))

(defn looper [nome sound]
  (let [beat (nome)]
    (at (nome beat) (sound))
    (apply-by (nome (inc  beat)) looper nome sound [])))

(looper one-twenty-bpm kick)
(chord-progression-beat one-twenty-bpm)
(stop)
