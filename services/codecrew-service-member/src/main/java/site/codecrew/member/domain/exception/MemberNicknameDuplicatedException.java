package site.codecrew.member.domain.exception;

import site.codecrew.member.domain.Nickname;

public class MemberNicknameDuplicatedException extends MemberException {

    public MemberNicknameDuplicatedException(Nickname nickname) {
        super(MemberErrorCode.NICKNAME_DUPLICATED, "Nickname already in use", nickname.value());
    }
}
