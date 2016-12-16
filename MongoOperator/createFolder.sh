n=10;
max=13;
while [ "$n" -le "$max" ]; do
  mkdir "$n"
  n=`expr "$n" + 1`;
done
