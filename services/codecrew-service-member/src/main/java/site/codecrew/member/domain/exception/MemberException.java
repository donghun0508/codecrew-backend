package site.codecrew.member.domain.exception;

import site.codecrew.core.exception.CoreException;
import site.codecrew.core.exception.ErrorCode;

public class MemberException extends CoreException {

    public MemberException(MemberErrorCode code) {
        super(code);
    }

    public MemberException(MemberErrorCode code, String detailMessage) {
        super(code, detailMessage);
    }

    public MemberException(ErrorCode code, String reason, Object... data) {
        super(code, reason, data);
    }

    public MemberException(MemberErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
