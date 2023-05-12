package pl.coderslab.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCreationInfo {
    @NotNull
    @Size(min = 1, max = 120)
    private String title;

    @NotNull
    @Size(min = 1, max = 250)
    private String content;
}
