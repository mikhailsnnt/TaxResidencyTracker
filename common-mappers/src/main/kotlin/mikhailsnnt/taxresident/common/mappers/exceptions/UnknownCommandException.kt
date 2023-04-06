package mikhailsnnt.taxresident.common.mappers.exceptions

import mikhailsnnt.taxresident.common.model.enums.TxCommand


class UnknownCommandException(command: TxCommand): RuntimeException("Command $command cannot be mapped to response")