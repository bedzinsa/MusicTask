package com.arunasbedzinskas.musictask.ext

import io.mockk.MockKVerificationScope
import io.mockk.Ordering
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.reflect.KClass

inline fun <reified T : Any> mockkRelaxed(
    name: String? = null,
    vararg moreInterfaces: KClass<*>,
    block: T.() -> Unit = {}
): T = mockk<T>(
    name = name,
    relaxed = true,
    moreInterfaces = moreInterfaces,
    relaxUnitFun = true,
    block = block
)

fun coVerifyNone(
    ordering: Ordering = Ordering.UNORDERED,
    inverse: Boolean = false,
    timeout: Long = 0,
    verifyBlock: suspend MockKVerificationScope.() -> Unit
) = coVerify(
    ordering = ordering,
    inverse = inverse,
    timeout = timeout,
    exactly = 0,
    verifyBlock = verifyBlock
)
