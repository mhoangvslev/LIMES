#/bin/sh

echo "Generating left.ttl using tarql..."
cat left*.csv | uniq | tarql -q --header-row --stdin tarql_left.sparql > left.ttl

echo "Generating right.ttl using tarql..."
tarql -q --header-row tarql_right.sparql right.csv > right.ttl

echo "Generating gt.ttl using tarql..."
tarql -q --header-row tarql_gt.sparql gt.csv > gt.ttl