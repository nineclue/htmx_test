package htmx

import scalatags.Text.all.*

object HTMX:
    val t = div(data.columns := 3)
    val hxget = attr("hx-get")
    val hxpost = attr("hx-post")
    val hxput = attr("hx-put")
    val hxpatch = attr("hx-patch")
    val hxdelete = attr("hx-delete")

    val hxtrigger = attr("hx-trigger")

    val hxindicator = attr("hx-indicator")

    val hxtarget = attr("hx-target")

    val hxswap = attr("hx-swap")

    val hxsync = attr("hx-sync")

    val hxswapoob = attr("hx-swap-oob")

    val hxselect = attr("hx-select")
    val hxselectoob = attr("hx-select-oob")

    val hxpreserve = attr("hx-preserve")

    val hxinclude = attr("hx-include")
    val hxparams = attr("hx-params")

    val hxencoding = attr("hx-encoding")

    val hxvals = attr("hx-vals")
    val hxvars = attr("hx-vars")

    val hxconfirm = attr("hx-confirm")

    val hxdisinherit = attr("hx-disinherit")
    
    val hxboost = attr("hx-boost")

    val hxws = attr("hx-ws")

    val hxsse = attr("hx-sse")

    val hxpushurl = attr("hx-push-url")
    val hxhistoryelt = attr("hx-history-elt")
    val hxhistory = attr("hx-history")

    val hxprompt = attr("hx-prompt")

    val hxvalidate = attr("hx-validate")

    val hxext = attr("hx-ext")

    val hxon = attr("hx-on")
