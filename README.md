# DaMa
Minecraft Plugin with own created teams

ToDo

Befehle:
- Event-Liste anzeigen
- Team 1,2,3 kann beigetreten werden
- Spiel starten/pausieren/beenden
- Events aktivieren/deaktivieren

1. Beim betreten des Servers
- Team 1,2,3 werden fest erstellt, Team größe: 2
- Spieler muss Team manuell auswählen
- Teamgröße ist default auf 2, kann aber mit Befehl geändert werden
- Jeder Spieler muss in einem Team sein
- Spieler landen in einer Lobby und werden beim starten an ihren Startpunkt teleportiert

2. Beim verlassen des Spiels ohne das es zu Ende ist
- Kordinaten der Spieler speichern (Datei?)
- zurück in Lobby teleportieren bis Spiel wieder gestartet wurde (hierbei prüfen ob aktives Spiel bereits besteht)

3. Scoreboard
   -> Ausgabe über Chat wäre am einfachsten

A)
- Teamname + Mitglieder
- Koordinaten Teammitglieder
- Position von Teams (Koordinaten X, Y, Z)
  Alle Spieler eines Teams Koordinaten zusammenrechnen und durch Anzahl teilen
- Abstand von Teams (Differenz X und Y)
  Satz des Phytagoras
- Startzeitpunkt (Datum, Uhrzeit)
  Aktive Events, anhaltende Events werden angezeigt + Ablauf Timer (verbleibende Zeit)

Position rechts mittig

B)
Alle Events anzeigen (Status)

4. Events
- Allgemein: alle 3-6 Minuten wird ein "Event" ausgelöst (nur wenn Spiel läuft), dies hält je nach Event ein Zeit t an
  Events, können sich überlagern (Zeitgleich sind mehrere Events aktiv
- Kisten spawnen (mit Items) an öffentlchen (koords)
- Giftiger regen -> spieler die regen abbekommen, werden vergiftet. Regen hält an (4-8 Minuten)
- Doppelte diaore dropp (12 Min aktiv)
- lange Nacht, 3 Tag in Folge ist Nacht. (time set nigth)
- Dreifacher mob dropp (4-8) Minuten
- Starke Mobs: Größe der neu gespwant mobs, variert zwischen Wert 1 und 3, es spwanen mehr Mops
- Event clear: Alle anhaltend Events werden geclead
- Event nix: kein Event, (nite)
- Event (Liste an Blöcke), die NICHT abgebautwerden können
- Event Corona: Halte mindestens 10 Blöcke abstand zu Teammitgliedern ein, wirst du vergiftet