package controllers

import play.api.cache._


class Templates @Inject() (
  cache: CacheApi,
  ws: WSClient,

