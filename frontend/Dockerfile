FROM node:lts-bookworm-slim
MAINTAINER Ana

# Install the application
ADD . /node
WORKDIR /node
ENV USER=appuser
ENV UID=12345
ENV GID=23456

RUN apk --no-cache add curl

RUN addgroup --gid "$GID" "$USER" && adduser \
    --disabled-password --gecos "" \
    --home "$(pwd)" --ingroup "$USER" \
    --no-create-home --uid "$UID" "$USER" && npm install
USER $USER
ENV WEB_PORT 3000
EXPOSE  3000

ENTRYPOINT ["node", "/node/app.js"]