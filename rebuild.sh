ECHO "javac src/*.java -d build"
javac src/*.java -d build

ECHO "jar cfe0 bin/freecell.jar FreeCell -C build ."
jar cfe0 bin/freecell.jar FreeCell -C build .

ECHO "jar cfe0 bin/cards.jar PlayingCardManager -C build ."
jar cfe0 bin/cards.jar PlayingCardManager -C build .

ECHO "Rebuilt freecell.jar and cards.jar"
