package mikhailsnnt.taxresident.common.mappers.exceptions

class UnknownRequestException(cls: Class<*>): RuntimeException("Class $cls cannot be mapped to TxContext")