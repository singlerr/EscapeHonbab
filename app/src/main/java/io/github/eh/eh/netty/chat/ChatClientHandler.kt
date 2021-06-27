package io.github.eh.eh.netty.chat

import io.github.eh.eh.netty.chat.bundle.MessageBundle
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import lombok.Getter

class ChatClientHandler : ChannelInboundHandlerAdapter {
    private var chatContext: ChatContext? = null

    @Getter
    private var context: ChannelHandlerContext? = null
    private var messageHandler: MessageHandler? = null

    private constructor(context: ChatContext) {
        chatContext = context
    }

    private constructor(messageHandler: MessageHandler) {
        this.messageHandler = messageHandler
    }

    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        context = ctx
        chatContext = ChatContext.Companion.getInstance(ctx)
    }

    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is MessageBundle) messageHandler!!.onMessageReceived(chatContext, msg)
    }

    companion object {
        fun getInstance(context: ChatContext): ChatClientHandler {
            return ChatClientHandler(context)
        }

        fun getInstance(messageHandler: MessageHandler): ChatClientHandler {
            return ChatClientHandler(messageHandler)
        }
    }
}