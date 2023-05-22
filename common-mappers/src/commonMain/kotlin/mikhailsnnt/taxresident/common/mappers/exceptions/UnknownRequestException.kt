package mikhailsnnt.taxresident.common.mappers.exceptions

import kotlin.reflect.KClass
class UnknownRequestException(cls: KClass<*>): RuntimeException("Class $cls cannot be mapped to TxContext")