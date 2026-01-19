package site.codecrew.member.application;

import site.codecrew.member.domain.IssuerSubjectIdentity;
import site.codecrew.member.domain.Nickname;

public record MemberNicknameUpdateCommand(
    IssuerSubjectIdentity issuerSubjectIdentity,
    Nickname nickname
) {

}
