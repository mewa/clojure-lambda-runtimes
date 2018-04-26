#!/bin/bash

set -eo pipefail

BENCH_PARALLEL=1

[ ! -z "$2" ] && BENCH_PARALLEL=$2

[ $BENCH_PARALLEL -gt 1 ] && echo Parallelism: $BENCH_PARALLEL

TYPE=$FN

[ -z $TYPE ] && echo No FN supplied && exit 1

[ ! -d out ] && mkdir out

function bench {
    for i in $(seq 1 $1); do
        echo Round $i
        aws lambda invoke --function-name clj-$TYPE --region eu-central-1 --log-type Tail --payload '"test"' out/$TYPE.$$.$i.out \
            | jq -r '.LogResult' | base64 -d | grep REPORT | cut -f 2 | grep -oe '[0-9].*$' >> $TYPE.clj &
        PID1=$!
        aws lambda invoke --function-name cljs-$TYPE --region eu-central-1 --log-type Tail --payload '"test"' out/$TYPE.$$.$i.out \
            | jq -r '.LogResult' | base64 -d | grep REPORT | cut -f 2 | grep -oe '[0-9].*$' >> $TYPE.cljs &
        PID2=$!
        echo Awaiting
        wait $PID1 $PID2
    done
}

for p in $(seq 1 $BENCH_PARALLEL); do
    bench $1 &
done
