package seedu.address.logic.commands.sale;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.sale.Sale;

/**
 * Lists all sales belonging to a specified contact.
 */
public class AllListCommand extends ListCommand {

    private final boolean showAll;
    private final Index targetIndex;

    /**
     * Creates an AllListCommand with the following parameters.
     * @param showAll A boolean dictating if all sales should be displayed.
     * @param targetIndex The index of the contact whose sales are to be displayed.
     */
    public AllListCommand(boolean showAll, Index targetIndex) {
        this.showAll = showAll;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> sortedPersonList = model.getSortedPersonList();
        List<Sale> sales = model.getFilteredSaleList();
        StringBuilder output = new StringBuilder();

        if (showAll) {
            model.updateFilteredSaleList(x -> true);
            output = output.append("Listing all sales:\n");

            if (sales.size() == 0) {
                return new CommandResult("No sales made!");
            }

        } else {
            if (targetIndex.getZeroBased() >= sortedPersonList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToShow = sortedPersonList.get(targetIndex.getZeroBased());
            Predicate<Sale> filterByContact = x -> x.getBuyerId() == personToShow.getId();

            model.updateFilteredSaleList(filterByContact);
            output = output.append("Sales made to ").append(personToShow.getName()).append(":\n");

            if (sales.size() == 0) {
                return new CommandResult("No sales made to " + personToShow.getName() + "!");
            }
        }

        int index = 1;
        for (Sale sale : sales) {
            output.append(index).append(". ").append(sale.toString()).append("\n");
            index++;
        }

        return new CommandResult(output.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof AllListCommand) {
            if (!showAll) {
                return targetIndex.equals(((AllListCommand) other).targetIndex);
            } else {
                return ((AllListCommand) other).showAll;
            }
        }

        return false;
    }
}