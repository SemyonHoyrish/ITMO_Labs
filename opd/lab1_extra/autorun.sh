dirname="autorun_$(date +%Y%m%d%H%M%S)"

echo "dirname $dirname"

ssh -l s467937 -p 2222 se.ifmo.ru "mkdir $dirname"
scp -P 2222 $1 s467937@se.ifmo.ru:~/$dirname
ssh -l s467937 -p 2222 se.ifmo.ru "cd $dirname && chmod +x ./$1 && ./$1 ${@:2}"

