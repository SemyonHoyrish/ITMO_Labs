cd lab0

# PART 1

echo -e 'Возможности\tOverland=2 Surface=1 Jump=2 Power=1\nIntelligence=3' > cleffa4
echo -e 'Возможности\tOverland=6 Surface=4 Jump=2\nPower=1 Intelligence=3 Pack Mon=0' > deerling6
echo -e 'Тип покемона\tWATER\nNONE' > floatzel7

mkdir -p hitmonlee9 \
	hitmonlee9/mamoswine\
	hitmonlee9/snivy \
	hitmonlee9/budew

cd hitmonlee9
echo -e 'Ходы\tEndeavor Giga Drain Gravity Iron Defense Iron\nHead Knock Off Magnet Rise Rock Climb Seed Bomb Sleep Talk Snore\nStealth Rock Worry Seed' > ferrothron
echo -e 'Способности\tSand-Attack Confusion\nQuick Attack Swift Psybeam Future Sight Psych Up Morning Sun Phychic\nLast Resort Power Swap' > espeon
echo -e 'Тип покемона\tBUG GRASS' > leavanny
cd ..

mkdir -p mamoswine3 \
	mamoswine3/glaceon \
	mamoswine3/silcoon \
	mamoswine3/combusken

cd mamoswine3
echo -e 'Ходы\nBody Slam Counter Double-Edge Dual Chop Fire Punch Focus Punch Helping\nHand Ice Punch Knock Off Low Kick Magic Coat Mega Kick Mega Punch\nMetronome Mud-Slap Role Play Sleep Talk Snore Superpower Thunderpunch\nVacuum Wave' > machoke
echo -e 'satk=10 sdef=9 spd=11' > electabuzz
cd ..

mkdir -p scizor7 \
	scizor7/ducklett \
	scizor7/kadabra

echo 'Тип диеты\nTerravore' > scizor7/golurk

# PART 2

chmod -vv  620 cleffa4
chmod -vv  u=rw,g=w,o= deerling6
chmod -vv  ugo=r floatzel7
chmod -vv  577 hitmonlee9
cd hitmonlee9
chmod -vv  551 mamoswine
chmod -vv  440 ferrothron
chmod -vv  660 espeon
chmod -vv  751 snivy
chmod -vv  604 leavanny 
chmod -vv  355 budew
cd ..
chmod -vv  737 mamoswine3
cd mamoswine3
chmod -vv  571 glaceon
chmod -vv  ug=wx,o=rx silcoon
chmod -vv  660 machoke
chmod -vv  640 electabuzz
chmod -vv  311 combusken
cd ..
chmod -vv  524 scizor7
cd scizor7
chmod -vv  550 ducklett
chmod -vv  576 kadabra
chmod -vv  u=,g=r,o=wr golurk
cd ..

# PART 3

cp floatzel7 mamoswine3/combusken
chmod u+r hitmonlee9/budew
cp -R hitmonlee9 hitmonlee9/budew
chmod u-r hitmonlee9/budew
chmod u+w hitmonlee9
ln -s deerling6 hitmonlee9/espeondeerling
chmod u-w hitmonlee9
cat cleffa4 > mamoswine3/electabuzzcleffa
ln -s hitmonlee9 Copy_16
chmod u+w scizor7
ln floatzel7 scizor7/golurkfloatzel
chmod u-w scizor7
chmod u+r scizor7/golurk
cat hitmonlee9/espeon scizor7/golurk > deerling6_67
chmod u-r scizor7/golurk

# PART 4
cd ..

cat $(find lab0 -name "*e" -type f) | wc -l > /tmp/res.txt

ls -lS $(find lab0 -type f) | head -n 4

cat lab0/hitmonlee9/espeon lab0/hitmonlee9/leavanny lab0/mamoswine3/machoke | grep -E "[fF]{2}" 2>/dev/null

cat lab0/scizor7/* 2>/tmp/errlog.txt | grep 'n$'

ls -l $(find lab0 -type f -name "*le*" 2>/tmp/errlog.txt | sort -r | tail -n 3)

ls -l $(find lab0 -type f -name "*bu*" | sort | head -n 3)

cd lab0

# PART 5

rm deerling6
rm mamoswine3/electabuzz
chmod u+w hitmonlee9
rm hitmonlee9/espeondeerli*
chmod u+w scizor7
rm -f scizor7/golurkfloatz*
chmod u-w scizor7
chmod -R u+wrx hitmonlee9
rm -rf hitmonlee9

