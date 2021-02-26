# Runeterra Latest Videos Contributing Guide

First off, thank you for considering contributing to Runeterra Latest Videos. It’s people like you that make Runeterra Latest Videos such a great tool.

Before submitting your contribution, please make sure to take a moment and read through the following guidelines:

## Where do I go from here?

If you've noticed a bug or have a feature request, [make one!](url-to-new-issue) 
It's generally best if you get confirmation of your bug or approval for your feature request this way before starting to code.

## Fork & create a branch

If this is something you think you can fix, then fork Runeterra Latest Videos and create a branch with a descriptive name.

A good branch name would be (considering the issue #123 is the ticket you're working on):

```
git checkout -b 123-add-portuguese-translation
```

## Commit and Pull Request guideline

A good commit message looks like this:

```
Header line: explaining the commit in one line

Body of commit message is a few lines of text, explaining things
in more detail, possibly giving some background about the issue
being fixed, etc etc.

The body of the commit message can be several paragraphs, and
please do proper word-wrap and keep columns shorter than about
74 characters or so. That way "git log" will show things
nicely even when it's indented.

Signed-off-by: Your Name <youremail@yourhost.com>
```

A good pull request 

## Development Setup

- npm 
- Java 15

## Project Structure

### Frontend

- **`src`**: contains the source code.

  - **`assets`**: contains the assets.
    
  - **`components`**: contains the Vue components separated by folders.
    
      - **`filter`**: contains the components related to the filter.
        
      - **`shared`**: contains the components that can be shared.
        
      - **`video`**: contains the components related to the videos.
    
  - **`service`**: contains the service responsible to call the backend.
    
  - **`store`**: contains the vuex modules. 


### Backend

Considering `info.gamewise.lor.videos`

- **`config`** contains the properties and config classes
  
- **`controller`** contains the `@RestController` class

- **`deckcodeextractor`** contains the components to extract the deck code from the video description
  
- **`domain`** contains the domain classes
  
- **`entity`** contains the classes related to the database. They **MUST** be package private.
  
- **`fetcher`** contains the logic to fetch videos from YouTube.
  
- **`service`** contains the implementation of the uses cases.
  
- **`usecase`** contains the uses cases that should be implemented by a service or the persistence adapter.

As you may notice, I am using a Use Case interface for each functionality. Also, the JPA classes must be not exposed out of the `entity` package.