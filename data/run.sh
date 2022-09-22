#/bin/sh
DATASET=$1; shift
docker run -it --rm -v $(pwd):/data limes-wordnet /data/configuration-$DATASET.xml