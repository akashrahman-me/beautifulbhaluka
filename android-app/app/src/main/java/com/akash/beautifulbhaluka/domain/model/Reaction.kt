package com.akash.beautifulbhaluka.domain.model

/**
 * Reaction types similar to Facebook reactions
 */
enum class Reaction(val emoji: String, val label: String) {
    LIKE("👍", "Like"),
    LOVE("❤️", "Love"),
    HAHA("😂", "Haha"),
    WOW("😮", "Wow"),
    SAD("😢", "Sad"),
    ANGRY("😡", "Angry"),
    CUSTOM("", "Custom");

    companion object {
        /**
         * Extended list of custom emojis users can choose from
         */
        val customEmojis = listOf(
            "😛" to "Tongue",
            "🤡" to "Clown",
            "👊" to "Fist",
            "✌" to "Peace",
            "👏" to "Clap",
            "🤲" to "Palms",
            "🙏" to "Prayer",
            "🤝" to "Handshake",
            "💪" to "Strong",
            "🐈" to "Cat",
            "🐯" to "Tiger",
            "🐆" to "Leopard",
            "🌹" to "Rose",
            "🌺" to "Hibiscus",
            "🍆" to "Eggplant",
            "🌾" to "Rice",
            "🛶" to "Canoe",
            "🍼" to "Bottle",
            "🔪" to "Knife",
            "⚽" to "Soccer",
            "🇧🇩" to "Bangladesh",
            "🤔" to "Thinking",
            "🥱" to "Yawn",
            "😜" to "Wink",
            "🖕" to "Finger",
            "🫶" to "Heart Hands",
            "😍" to "Heart Eyes",
            "🔥" to "Fire"
        )
    }
}
