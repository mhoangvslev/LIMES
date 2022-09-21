#/bin/sh

echo "Generating left.ttl using tarql..."
tarql -q --header-row tarql_left.sparql left.csv > left.ttl
tarql -q --header-row tarql_left.sparql left_aug.csv | sed -n '3,$p' >> left.ttl

echo "Generating right.ttl using tarql..."
tarql -q --header-row tarql_right.sparql right.csv > right.ttl

echo "Generating gt.ttl using tarql..."
tarql -q --header-row tarql_gt.sparql gt.csv > gt.ttl