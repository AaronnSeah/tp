package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.InvalidTagListTypeException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * A list of tags for contacts that enforces uniqueness between its elements and does not allow nulls.
 * A tag is considered unique by comparing using {@code Tag#isSameTag(Tag)}. As such, adding and updating of
 * tags uses Tag#isSameTag(Tag) for equality so as to ensure that the tag being added or updated is
 * unique in terms of identity in the UniqueContactTagList. However, the removal of a tag uses Tag#equals(Object) so
 * as to ensure that the tag with exactly the same tag name will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Tag#isSameTag(Tag)
 */
public class UniqueContactTagList extends UniqueTagList {

    public UniqueContactTagList() {
        super();
    }

    @Override
    public boolean belongsToContact() {
        return true;
    }

    @Override
    public boolean belongsToSale() {
        return false;
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     * {@code replacement} must be another UniqueContactTagList.
     */
    @Override
    public void setTags(UniqueTagList replacement) {
        requireNonNull(replacement);
        if (replacement.belongsToContact()) {
            internalList.setAll(replacement.internalList);
        } else {
            throw new InvalidTagListTypeException();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueContactTagList // instanceof handles nulls
                && internalList.equals(((UniqueContactTagList) other).internalList));
    }
}
