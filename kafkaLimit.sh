#!/bin/sh

command="du -sh /tmp/kafka-logs/video-stream*"
log="size.log"

$command > "$log" 2>&1 &
echo "Starting program..."
while sleep 10; do
        echo "Looping..."
        type=`cat "$log" | sed -e 's/\t.*//' |sed -E 's/^([0-9]+)([,]*)([0-9]*)([A-Z]+)/\4/'`
        if [ "$type" = "G" ]; then
                echo "Too big (>G). Removing..."
                rm -rf /tmp/kafka-logs/video-stream*
        fi

        if [ "$type" = "M" ]; then
                size=`cat "$log" | sed -e 's/\t.*//' |sed -E 's/^([0-9]+)([,]*)([0-9]*)([A-Z]+)/\1/'`
                if [ "$size" -ge 500 ]; then
                        echo "Too big (>500M). Removing..."
                       rm -rf /tmp/kafka-logs/video-stream*
                fi
        fi


done < "$log"
