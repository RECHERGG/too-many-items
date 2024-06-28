package de.rechergg.toomanyitems.extension

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage

fun cmp(text: String, color: TextColor = NamedTextColor.GRAY) = Component.text(text, color)

operator fun Component.plus(other: Component) = append(other)

fun prefix() = cmp("[", NamedTextColor.DARK_GRAY) + "<gradient:#32CD32:#228B22:#006400>TooManyItems<gradient/>".minimessage() + cmp("]", NamedTextColor.DARK_GRAY) + cmp(" ")

fun String.minimessage() = MiniMessage.miniMessage().deserialize(this)