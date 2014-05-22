(ns showerphone.core
  (:require [overtone.live :refer :all]
            [overtone.inst.sampled-piano :refer :all]))

(defn play-chord [chord]
  (doseq [note chord] (sampled-piano note)))

(defonce one-twenty-bpm (metronome 120))

(defn chord-progression-beat [nome chord-progression]
  (let [beat (nome)]
    (doseq [[base scale interval] chord-progression]
      (at (nome (+ interval beat)) (play-chord (chord base scale))))
    (apply-by (nome (+ 8 beat)) chord-progression-beat nome chord-progression [])))

(defsample kick
  (freesound-path 777))

(defsample boom
  (freesound-path 33637))

(defsample close-hat
  (freesound-path 802))

(defsample open-hat
  (freesound-path 26657))

(defn looper [nome intervals sound]
  (let [beat (nome)]
    (doseq [interval intervals]
      (at (nome (+ interval beat)) (sound)))
    (apply-by (nome (+ 8 beat)) looper nome intervals sound [])))

(defn kit []
  (looper one-twenty-bpm
          [0 1 2 3 4 5 6 7]
          kick)
  (looper one-twenty-bpm
          [7.5]
          close-hat)
  (looper one-twenty-bpm
          [8]
          open-hat))

(boom)

(chord-progression-beat one-twenty-bpm
                        '([:C4 :major 0]
                          [:G3 :major 4]
                          [:F3 :sus4  6]))

(kit)

(stop)

;(recording-start "/Users/jithu/code/showerphone/sample.wav")
;(recording-stop)
