FROM ubuntu:latest
LABEL authors="bpatt"

ENTRYPOINT ["top", "-b"]