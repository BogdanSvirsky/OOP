package ru.nsu.svirsky.interfaces;

/**
 * Interface for retrieving an ID of a specified type.
 *
 * @param <IdType> The type of the ID.
 * @author BogdanSvirsky
 */
public interface IdGetter<IdType> {
    /**
     * Retrieves the ID.
     *
     * @return The ID of type {@link IdType}.
     */
    IdType get();
}