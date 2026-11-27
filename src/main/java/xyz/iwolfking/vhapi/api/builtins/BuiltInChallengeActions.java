package xyz.iwolfking.vhapi.api.builtins;

import iskallia.vault.core.vault.challenge.action.ReferenceChallengeAction;
import xyz.iwolfking.vhapi.mixin.accessors.ReferenceChallengeActionInvoker;

public class BuiltInChallengeActions {
    public static ReferenceChallengeAction referenceChallengeAction(String actionId) {
        return ReferenceChallengeActionInvoker.create(new ReferenceChallengeAction.Config(actionId));
    }
}
