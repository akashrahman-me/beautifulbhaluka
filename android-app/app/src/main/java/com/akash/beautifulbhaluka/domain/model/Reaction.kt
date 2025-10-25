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
            "🔥" to "Fire",
            "🎉" to "Party",
            "💯" to "Hundred",
            "🙏" to "Prayer",
            "👏" to "Clap",
            "💪" to "Strong",
            "🤔" to "Thinking",
            "😍" to "Heart Eyes",
            "🥰" to "Smiling Hearts",
            "😊" to "Smiling",
            "😎" to "Cool",
            "🤗" to "Hugging",
            "🤩" to "Star Eyes",
            "😇" to "Angel",
            "🥳" to "Party Face",
            "😴" to "Sleeping",
            "🤯" to "Mind Blown",
            "😱" to "Scream",
            "🤐" to "Zipper Mouth",
            "🙄" to "Eye Roll",
            "😏" to "Smirk",
            "🤨" to "Raised Eyebrow",
            "🧐" to "Monocle",
            "💀" to "Skull"
        )
    }
}
