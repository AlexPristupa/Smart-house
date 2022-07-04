#!/bin/bash
echo "Preparing postgresql db dump"
pg_dump -U smarthouse -F custom -b -O -E UTF8 -v -f /tmp/postgresql/backups/dump_"$(date +%d-%m-%Y_%H_%M_%S%Z)".backup smarthouse
